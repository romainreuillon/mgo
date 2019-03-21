/**
 * Created by reuillon on 07/01/16.
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
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package mgo.evolution

import cats.data._
import cats.implicits._
import mgo.evolution.contexts._
import squants.time._

object stop {

  type StopCondition[M[_], I] = Kleisli[M, Vector[I], Boolean]

  trait Imports {

    def afterGeneration[M[_]: cats.Monad, I](g: Long)(implicit mGeneration: Generation[M]): StopCondition[M, I] =
      Kleisli { (individuals: Vector[I]) =>
        for {
          cg <- mGeneration.get
        } yield cg >= g
      }

    def afterDuration[M[_]: cats.Monad: System, I](d: Time)(implicit mStartTime: StartTime[M]): StopCondition[M, I] =
      Kleisli { (individuals: Vector[I]) =>
        for {
          st <- mStartTime.get
          now <- System[M].currentTime()
        } yield (d.millis + st) <= now
      }

    def never[M[_]: cats.Monad, I]: StopCondition[M, I] = Kleisli { _ => false.pure[M] }
  }
}