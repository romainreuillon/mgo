/*
 * Copyright (C) 15/12/2015 Guillaume Chérel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.iscpif.mgo.algorithm

import fr.iscpif.mgo
import fr.iscpif.mgo._
import monocle.macros.{ Lenses, GenLens }
import ranking._
import niche._
import contexts._
import elitism._
import breeding._
import GenomeVectorDouble._

import scala.math._
import scala.util.Random
import scalaz._
import Scalaz._

import scala.language.higherKinds

object noisyprofile extends niche.Imports {

  @Lenses case class Genome(values: Array[Double], operator: Maybe[Int])
  @Lenses case class Individual(genome: Genome, historyAge: Long, fitnessHistory: Array[Double], age: Long)

  def vectorValues = Genome.values composeLens arrayToVectorLens
  def vectorFitness = Individual.fitnessHistory composeLens arrayToVectorLens

  def buildGenome(values: Vector[Double], operator: Maybe[Int]) = Genome(values.toArray, operator)
  def buildIndividual(g: Genome, f: Double) = Individual(g, 1, Array(f), 0)

  def initialGenomes(lambda: Int, genomeSize: Int): EvolutionState[Unit, Vector[Genome]] =
    GenomeVectorDouble.randomGenomes[EvolutionState[Unit, ?], Genome](buildGenome)(lambda, genomeSize)

  def breeding(lambda: Int, niche: Niche[Individual, Int], operatorExploration: Double, cloneProbability: Double, aggregation: Vector[Double] => Double): Breeding[EvolutionState[Unit, ?], Individual, Genome] =
    noisyprofileOperations.breeding[EvolutionState[Unit, ?], Individual, Genome](
      vectorFitness.get, aggregation, Individual.genome.get, vectorValues.get, Genome.operator.get, buildGenome
    )(lambda = lambda, niche = niche, operatorExploration = operatorExploration, cloneProbability = cloneProbability)

  def expression(fitness: (Random, Vector[Double]) => Double): Expression[(Random, Genome), Individual] =
    noisyprofileOperations.expression[Genome, Individual](vectorValues.get, buildIndividual)(fitness)

  def elitism(muByNiche: Int, niche: Niche[Individual, Int], historySize: Int, aggregation: Vector[Double] => Double): Elitism[EvolutionState[Unit, ?], Individual] =
    noisyprofileOperations.elitism[EvolutionState[Unit, ?], Individual](
      history = vectorFitness,
      aggregation = aggregation,
      values = (Individual.genome composeLens vectorValues).get,
      age = Individual.age,
      historyAge = Individual.historyAge
    )(muByNiche, niche, historySize)

  def profile(population: Vector[Individual], niche: Niche[Individual, Int]) =
    noisyprofileOperations.profile(population, niche, Individual.historyAge.get)

  object NoisyProfile {

    implicit def isAlgorithm = new Algorithm[NoisyProfile, EvolutionState[Unit, ?], Individual, Genome, EvolutionData[Unit]] {
      def initialState(t: NoisyProfile, rng: Random) = EvolutionData[Unit](random = rng, s = ())

      def initialPopulation(t: NoisyProfile) =
        stochasticInitialPopulation[EvolutionState[Unit, ?], Genome, Individual](
          noisyprofile.initialGenomes(t.lambda, t.genomeSize),
          noisyprofile.expression(t.fitness))

      def step(t: NoisyProfile) = {
        def breeding =
          noisyprofile.breeding(
            lambda = t.lambda,
            niche = t.niche,
            operatorExploration = t.operatorExploration,
            cloneProbability = t.cloneProbability,
            aggregation = t.aggregation
          )

        def elitism =
          noisyprofile.elitism(
            muByNiche = t.muByNiche,
            niche = t.niche,
            historySize = t.historySize,
            aggregation = t.aggregation
          )

        noisyprofileOperations.step[EvolutionState[Unit, ?], Individual, Genome](
          breeding,
          noisyprofile.expression(t.fitness),
          elitism)
      }

      def run[A](x: EvolutionState[Unit, A], s: EvolutionData[Unit]): (EvolutionData[Unit], A) = mgo.unwrap(x, s)
    }

  }

  case class NoisyProfile(
    muByNiche: Int,
    lambda: Int,
    fitness: (Random, Vector[Double]) => Double,
    aggregation: Vector[Double] => Double,
    niche: Niche[Individual, Int],
    genomeSize: Int,
    historySize: Int = 100,
    cloneProbability: Double = 0.2,
    operatorExploration: Double = 0.1)

  case class OpenMOLE(
    mu: Int,
    niche: Niche[Individual, Int],
    operatorExploration: Double,
    genomeSize: Int,
    historySize: Int,
    cloneProbability: Double,
    aggregation: Vector[Double] => Double)

  object OpenMOLE {
    implicit def integration = new openmole.Integration[OpenMOLE, Vector[Double], Double] with openmole.Stochastic with openmole.Profile[OpenMOLE] {
      type M[A] = EvolutionState[Unit, A]
      type G = Genome
      type I = Individual
      type S = EvolutionData[Unit]

      def iManifest = implicitly
      def gManifest = implicitly
      def sManifest = implicitly
      def mMonad = implicitly
      def mGenerational = implicitly
      def mStartTime = implicitly

      def operations(om: OpenMOLE) = new Ops {
        def randomLens = GenLens[S](_.random)
        def startTimeLens = GenLens[S](_.startTime)
        def generation(s: EvolutionData[Unit]) = s.generation
        def values(genome: G) = vectorValues.get(genome)
        def genome(i: I) = Individual.genome.get(i)
        def phenotype(individual: I): Double = om.aggregation(vectorFitness.get(individual))
        def buildIndividual(genome: G, phenotype: Double) = noisyprofile.buildIndividual(genome, phenotype)
        def initialState(rng: Random) = EvolutionData[Unit](random = rng, s = ())
        def initialGenomes(n: Int): EvolutionState[Unit, Vector[Genome]] = noisyprofile.initialGenomes(n, om.genomeSize)
        def breeding(n: Int): Breeding[EvolutionState[Unit, ?], Individual, Genome] = noisyprofile.breeding(n, om.niche, om.operatorExploration, om.cloneProbability, om.aggregation)
        def elitism: Elitism[EvolutionState[Unit, ?], Individual] = noisyprofile.elitism(om.mu, om.niche, om.historySize, om.aggregation)

        def migrateToIsland(population: Vector[I]) = population.map(_.copy(historyAge = 0))
        def migrateFromIsland(population: Vector[I]) =
          population.filter(_.historyAge != 0).map {
            i => Individual.fitnessHistory.modify(_.take(math.min(i.historyAge, om.historySize).toInt))(i)
          }
      }

      def unwrap[A](x: EvolutionState[Unit, A], s: EvolutionData[Unit]): (EvolutionData[Unit], A) = mgo.unwrap(x, s)

      def samples(i: I): Long = i.fitnessHistory.size
      def profile(om: OpenMOLE)(population: Vector[I]) = noisyprofile.profile(population, om.niche)
    }
  }

}

object noisyprofileOperations {

  def aggregatedFitness[I](fitness: I => Vector[Double], aggregation: Vector[Double] => Double)(i: I): Vector[Double] =
    Vector(aggregation(fitness(i)), 1.0 / fitness(i).size.toDouble)

  def breeding[M[_]: Monad: RandomGen: Generational, I, G](
    history: I => Vector[Double],
    aggregation: Vector[Double] => Double,
    genome: I => G,
    genomeValues: G => Vector[Double],
    genomeOperator: G => Maybe[Int],
    buildGenome: (Vector[Double], Maybe[Int]) => G)(
      lambda: Int,
      niche: Niche[I, Int],
      operatorExploration: Double,
      cloneProbability: Double): Breeding[M, I, G] =
    for {
      population <- Kleisli.ask[M, Vector[I]]
      operatorStatistics <- operatorProportions[M, I](genome andThen genomeOperator)
      gs <- tournament(
        ranking = paretoRankingMinAndCrowdingDiversity[M, I](aggregatedFitness(history, aggregation)),
        size = lambda + 1,
        rounds = size => math.round(math.log10(size).toInt)
      ) andThen
        pairConsecutive andThen
        mapPureB { case (g1, g2) => ((genome andThen genomeValues)(g1), (genome andThen genomeValues)(g2)) } andThen
        applyDynamicOperator(operatorStatistics, operatorExploration) andThen
        flatMapPureB { case ((g1, g2), op) => Vector((g1, op), (g2, op)) } andThen
        randomTakeLambda(lambda) andThen
        clamp(GenLens[(Vector[Double], Int)](_._1)) andThen
        mapPureB { case (g, op) => buildGenome(g, Maybe.just(op)) } andThen
        clonesReplace(cloneProbability, population, genome)
    } yield gs

  def elitism[M[_]: Monad: RandomGen: Generational, I](
    history: monocle.Lens[I, Vector[Double]],
    aggregation: Vector[Double] => Double,
    values: I => Vector[Double],
    age: monocle.Lens[I, Long],
    historyAge: monocle.Lens[I, Long])(muByNiche: Int, niche: Niche[I, Int], historySize: Int): Elitism[M, I] =
    applyCloneStrategy(values, mergeHistories[M, I, Double](historyAge, history)(historySize)) andThen
      filterNaN(aggregatedFitness(history.get, aggregation)) andThen
      keepNiches[M, I, Int](
        niche = niche,
        objective = keepHighestRanked(
          paretoRankingMinAndCrowdingDiversity[M, I](aggregatedFitness(history.get, aggregation)),
          muByNiche)
      ) andThen incrementGeneration(age)

  def expression[G, I](
    values: G => Vector[Double],
    builder: (G, Double) => I)(fitness: (Random, Vector[Double]) => Double): Expression[(Random, G), I] = {
    case (rg, g) => builder(g, fitness(rg, values(g)))
  }

  def step[M[_]: Monad: RandomGen: Generational: ParallelRandomGen, I, G](
    breeding: Breeding[M, I, G],
    expression: Expression[(Random, G), I],
    elitism: Elitism[M, I]): Kleisli[M, Vector[I], Vector[I]] = noisyStep(breeding, expression, elitism)

  def profile[I](population: Vector[I], niche: Niche[I, Int], historyAge: I => Long): Vector[I] =
    population.groupBy(niche).toVector.unzip._2.map {
      _.maxBy(historyAge)
    }
}