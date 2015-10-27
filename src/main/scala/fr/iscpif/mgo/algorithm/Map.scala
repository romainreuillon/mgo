/*
 * Copyright (C) 13/11/13 Romain Reuillon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.iscpif.mgo.algorithm

import fr.iscpif.mgo._
import scalaz.State

trait Map <: Algorithm with GeneticAlgorithm with AllFunctions with MapFunctions {

  case class MapState(hitMap: collection.Map[(Int, Int), Int])
  type STATE = MapState
  def initialState = MapState(collection.Map())

  implicit val hits = monocle.macros.Lenser[MapState](_.hitMap)
  implicit val fitness: Fitness[Double]
  implicit val plotter: Plotter[(Int, Int)]

  override def breeding(pop: Pop): State[AlgorithmState, Vector[G]] =
    onHitCount.apply(pop) flatMap { challenged =>
      val fight = tournament(challenged, pop, size => math.round(math.log10(size).toInt))

      val newGenome =
        for {
          s1 <- fight
          s2 <- fight
          c <- crossover(pop)(s1.genome, s2.genome)
          (c1, c2) = c
          g1 <- mutation(pop)(c1)
          g2 <- mutation(pop)(c2)
        } yield { Vector(clamp(g2), clamp(g2)) }

      newGenome.generateFlat(lambda)
    }

  override def elitism(population: Pop, offspring: Pop): State[AlgorithmState, Pop] =
    for {
      p1 <- merge(population, offspring)
      p2 <- nicheElitism(keepBestRanked(1), p1)
    } yield p2.age

}

