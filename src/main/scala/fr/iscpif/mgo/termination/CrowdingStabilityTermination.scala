/*
 * Copyright (C) 2011 Romain Reuillon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.iscpif.mgo.termination

import fr.iscpif.mgo._
import tools._
import math._

/**
 * Termination creterium computed from the variation of the maximum crowding distance
 * among the population elements. It stop when this metric stabilize.
 *
 * FIXME: take into account only the last ranked individual, pb it can be empty
 * due to the filter on the positive infinity diversity
 */
trait CrowdingStabilityTermination extends Termination with CrowdingDiversity with DiversityModifier with StabilityTermination {

  override def terminated(population: => Population[G, P, F, MF], terminationState: STATE): (Boolean, STATE) = {
    val maxCrowding = population.map { e => diversity.get(e.metaFitness)() }.filter(_ != Double.PositiveInfinity).max
    stability(terminationState, maxCrowding)
  }

}
