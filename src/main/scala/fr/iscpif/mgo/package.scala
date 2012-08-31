/*
 * Copyright (C) 2012 reuillon
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

package fr.iscpif

package object mgo {
  
  implicit def traversable2Population[G, I](seq: Traversable[PopulationElement[G, I]]) =
    new Population[G, I] {
      override val content = seq.toIndexedSeq
    }
  
  implicit def population2IndexedSeq[G, I](pop: Population[G, I]) = pop.content

  private def changeScale(v:Double, min:Double, max:Double, boundaryMin:Double, boundaryMax:Double) = {
    val factor = (boundaryMax - boundaryMin)  / (max - min)
    (factor * (v - min) + boundaryMin)
  }
  
  implicit def double2Scalable(d: Double) = new {
    def scale(min:Double, max:Double) = changeScale(d, 0, 1, min, max)
    def unscale(min: Double, max: Double) = changeScale(d, min, max, 0, 1)
  }
  
  type NSGAII = algorithm.NSGAII
  type SMSEMOEA = algorithm.SMSEMOEA
  type Breeding = breed.Breeding
  type AverageCrossOver = crossover.AverageCrossover
  type CrossOver = crossover.CrossOver
  type NoneCrossOver = crossover.NoneCrossOver
  type SBXBoundedCrossover = crossover.SBXBoundedCrossover
  type UniformCrossOver = crossover.UniformCrossOver
  type CrowdingDiversity = diversity.CrowdingDiversity
  type Diversity = diversity.Diversity
  type DiversityMetric = diversity.DiversityMetric
  type HypervolumeDiversity = diversity.HypervolumeDiversity
  type Dominance = dominance.Dominance
  type EpsilonDominance = dominance.EpsilonDominance
  type NonStrictDominance = dominance.NonStrictDominance
  type StrictDominance = dominance.StrictDominance
  type Elitism = elitism.Elitism
  type NonDominatedElitism = elitism.NonDominatedElitism
  type GAEvolution = ga.GAEvolution
  type GAG = ga.GAG
  type GAGenome = ga.GAGenome
  type GAGenomeWithSigma = ga.GAGenomeWithSigma
  type NSGAIISigma = ga.NSGAIISigma
  type SMSEMOEASigma = ga.SMSEMOEASigma
  type Sigma = ga.Sigma
  type SigmaGAEvolution = ga.SigmaGAEvolution
  val Hypervolume = metric.Hypervolume
  type ReferencePoint = metric.ReferencePoint
  type CloneRemoval = modifier.CloneRemoval
  type DiversityModifier = modifier.DiversityModifier
  type IndividualFilter = modifier.IndividualFilter
  type Modifier = modifier.Modifier
  type NoneModifier = modifier.NoneModifier
  type RankModifier = modifier.RankModifier
  type RankDiversityGenomicCrowdingModifier = modifier.RankDiversityGenomicCrowdingModifier
  type RankDiversityModifier = modifier.RankDiversityModifier
  type CoEvolvingSigmaValuesMutation = mutation.CoEvolvingSigmaValuesMutation
  type GaussianMutation = mutation.GaussianMutation
  type Mutation = mutation.Mutation
  type NoneMutation = mutation.NoneMutation
  type RandomMutation = mutation.RandomMutation
  type Problem = problem.Problem
  type GAProblem = problem.GAProblem
  type Scaling = problem.Scaling
  type ParetoRanking = ranking.ParetoRanking
  type Rank = ranking.Rank
  type Ranking = ranking.Ranking
  type BinaryTournamentSelection = selection.BinaryTournamentSelection
  type Selection = selection.Selection
  type CrowdingStabilityTermination = termination.CrowdingStabilityTermination
  type CounterTermination = termination.CounterTermination
  type FirstRankedSteadyTermination = termination.FirstRankedSteadyTermination
  type HyperVolumeStabilityTermination = termination.HyperVolumeStabilityTermination
  type StabilityTermination = termination.StabilityTermination
  type Termination = termination.Termination
  type TerminationManifest = termination.TerminationManifest
  type TimedTermination = termination.TimedTermination
  
}