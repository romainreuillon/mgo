/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openmole.tools.mgo.ga

import org.openmole.tools.mgo._

trait GAGenomeFactory  [G <: GAGenome] extends GenomeFactory [G] {
  def buildGenome (values: IndexedSeq [Double]) : G
}
