/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openmole.tools.mgo

abstract class CrossOver [G <: AbstractGenome, F <: GenomeFactory [G]]
  extends Operator [G, F] 