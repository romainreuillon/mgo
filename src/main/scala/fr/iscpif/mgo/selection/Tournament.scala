/*
 * Copyright (C) 09/02/14 Romain Reuillon
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

package fr.iscpif.mgo.selection

import fr.iscpif.mgo._
import scala.util.Random

trait Tournament <: Selection {

  type Evaluation
  type IndividualEvaluation = (Individual[G, P, F], Evaluation)

  def evaluate(population: Population[G, P, F], archive: A): Seq[Evaluation]
  def tournament(e1: IndividualEvaluation, e2: IndividualEvaluation)(implicit rng: Random): IndividualEvaluation

}
