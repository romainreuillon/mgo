/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.iscpif.mgo

trait Archive { this: Evolution =>
  def archiveSize: Int
}