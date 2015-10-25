///*
// * Copyright (C) 2014 Romain Reuillon
// *
// * This program is free software: you can redistribute it and/or modify
// * it under the terms of the GNU Affero General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program.  If not, see <http://www.gnu.org/licenses/>.
// */
//
//package fr.iscpif.mgo.test
//
//import fr.iscpif.mgo._
//import fr.iscpif.mgo.tools.metric.CrowdingDistance
//
//object TestCrowding extends App {
//
//  import Double.{ PositiveInfinity => Infinity }
//  import rng._
//
//  val l = Seq(Seq(105.0, 3.9281819501704787, Infinity),
//    Seq(59.0, 4.1533705644106815, Infinity),
//    Seq(192.0, 3.4909669568553605, Infinity),
//    Seq(703.0, 3.352101028528309, Infinity),
//    Seq(207.0, 3.4491245404849398, Infinity),
//    Seq(846.0, 3.3278220569846226, Infinity),
//    Seq(157.0, 3.6253373721039184, Infinity),
//    Seq(842.0, 3.328772384894855, Infinity),
//    Seq(764.0, 3.330720420503126, Infinity),
//    Seq(12805.0, 2.761798941509222, Infinity),
//    Seq(202.0, 3.472010661388038, Infinity),
//    Seq(147.0, 3.684644572656879, Infinity),
//    Seq(321.0, 3.354884101406767, Infinity),
//    Seq(853.0, 3.3205661962825817, Infinity),
//    Seq(114.0, 3.8872025040842564, Infinity),
//    Seq(723.0, 3.34032212405029, Infinity),
//    Seq(93.0, 3.9509074404688134, Infinity),
//    Seq(12868.0, 2.7027462674912717, Infinity),
//    Seq(121.0, 3.8385143488003566, Infinity),
//    Seq(767.0, 3.33057720207222, Infinity),
//    Seq(12176.0, 3.0644425212513577, Infinity),
//    Seq(776.0, 3.330277607782449, Infinity),
//    Seq(874.0, 3.3173343504413775, Infinity),
//    Seq(140.0, 3.718304493092372, Infinity),
//    Seq(712.0, 3.347155309262977, Infinity),
//    Seq(884.0, 3.3144619445084165, Infinity),
//    Seq(1055.0, 3.2552422125759506, Infinity),
//    Seq(128.0, 3.830560773025354, Infinity),
//    Seq(966.0, 3.2990977747574295, Infinity),
//    Seq(731.0, 3.3394830564992173, Infinity),
//    Seq(161.0, 3.621829136438903, Infinity),
//    Seq(10.0, 14.221987821303822, Infinity),
//    Seq(12965.0, 2.672061817597294, Infinity),
//    Seq(1052.0, 3.260356609682824, Infinity),
//    Seq(56.0, 4.425194860299039, Infinity),
//    Seq(707.0, 3.3491467898404133, Infinity),
//    Seq(765.0, 3.3307030717021586, Infinity),
//    Seq(194.0, 3.482775164710624, Infinity),
//    Seq(196.0, 3.4797687940698196, Infinity),
//    Seq(12274.0, 2.999610148050382, Infinity),
//    Seq(40.0, 4.620833895660098, Infinity),
//    Seq(13907.0, 2.2473331407075263, Infinity),
//    Seq(89.0, 3.9622616759220097, Infinity),
//    Seq(753.0, 3.337386736518072, Infinity),
//    Seq(13435.0, 2.6100636150595293, Infinity),
//    Seq(45.0, 4.5362753884998455, Infinity),
//    Seq(87.0, 4.0414709218956695, Infinity),
//    Seq(1049.0, 3.2612491362592086, Infinity),
//    Seq(86.0, 4.0778653962820535, Infinity),
//    Seq(58.0, 4.375405771913604, Infinity),
//    Seq(691.0, 3.3534784021667896, Infinity),
//    Seq(132.0, 3.807766376926615, Infinity),
//    Seq(146.0, 3.699797327578291, Infinity),
//    Seq(1012.0, 3.2800230162083395, Infinity),
//    Seq(134.0, 3.803707787096588, Infinity),
//    Seq(49.0, 4.471050611201685, Infinity),
//    Seq(13487.0, 2.3717150863771073, Infinity),
//    Seq(90.0, 3.958224603285871, Infinity),
//    Seq(876.0, 3.3160958228814943, Infinity),
//    Seq(78.0, 4.128363475318257, Infinity),
//    Seq(213.0, 3.441669285865127, Infinity),
//    Seq(17.0, 8.45949541933453, Infinity),
//    Seq(209.0, 3.4419266575681915, Infinity),
//    Seq(193.0, 3.4845636491624927, Infinity),
//    Seq(1046.0, 3.2644205628052303, Infinity),
//    Seq(12871.0, 2.679447145509723, Infinity),
//    Seq(12.0, 11.380822685793554, Infinity),
//    Seq(152.0, 3.636272790455277, Infinity),
//    Seq(52.0, 4.441684416145673, Infinity),
//    Seq(13078.0, 2.6147178717036104, Infinity),
//    Seq(123.0, 3.8359326045289635, Infinity),
//    Seq(1040.0, 3.2659159861760885, Infinity),
//    Seq(1003.0, 3.281541190204035, Infinity),
//    Seq(43.0, 4.608483782854774, Infinity),
//    Seq(980.0, 3.293584363736274, Infinity),
//    Seq(12839.0, 2.7200441325854077, Infinity),
//    Seq(857.0, 3.319430304349523, Infinity),
//    Seq(881.0, 3.314836334413947, Infinity),
//    Seq(850.0, 3.3229875404980795, Infinity),
//    Seq(130.0, 3.8102538488332085, Infinity),
//    Seq(750.0, 3.3380839784109115, Infinity),
//    Seq(852.0, 3.321680045126573, Infinity),
//    Seq(129.0, 3.8164800544371937, Infinity),
//    Seq(188.0, 3.523442708982458, Infinity),
//    Seq(987.0, 3.2914247770804663, Infinity),
//    Seq(15.0, 8.93602374846511, Infinity),
//    Seq(12814.0, 2.7212092834499755, Infinity),
//    Seq(873.0, 3.318292613607915, Infinity),
//    Seq(13618.0, 2.2621282408060974, Infinity),
//    Seq(104.0, 3.9334025002463364, Infinity),
//    Seq(208.0, 3.443616773293246, Infinity),
//    Seq(113.0, 3.8874604357647504, Infinity),
//    Seq(1002.0, 3.283604640131684, Infinity),
//    Seq(880.0, 3.315509375454899, Infinity),
//    Seq(1004.0, 3.281245347137401, Infinity),
//    Seq(18.0, 8.30528921940045, Infinity),
//    Seq(716.0, 3.343341631603466, Infinity),
//    Seq(141.0, 3.712814153421232, Infinity),
//    Seq(851.0, 3.3222013749588912, Infinity),
//    Seq(136.0, 3.752474457974193, Infinity),
//    Seq(972.0, 3.29551272599291, Infinity),
//    Seq(1058.0, 3.2540198668175444, Infinity),
//    Seq(1030.0, 3.2755653096734143, Infinity),
//    Seq(216.0, 3.4413634926866097, Infinity),
//    Seq(138.0, 3.721675723652984, Infinity),
//    Seq(12107.0, 3.207910777559002, Infinity),
//    Seq(109.0, 3.8966864178181453, Infinity),
//    Seq(849.0, 3.3245972112332565, Infinity),
//    Seq(117.0, 3.884956628754332, Infinity),
//    Seq(13476.0, 2.4252815810365393, Infinity),
//    Seq(221.0, 3.4306699653993253, Infinity),
//    Seq(1022.0, 3.278498266318586, Infinity),
//    Seq(892.0, 3.314003690236044, Infinity),
//    Seq(993.0, 3.289102337277252, Infinity),
//    Seq(900.0, 3.3122612751566467, Infinity),
//    Seq(11.0, 11.934135165488613, Infinity),
//    Seq(855.0, 3.320388945093951, Infinity),
//    Seq(13554.0, 2.3543282554285785, Infinity),
//    Seq(1067.0, 3.2518586344575104, Infinity),
//    Seq(118.0, 3.884752566094863, Infinity),
//    Seq(107.0, 3.900264772607572, Infinity),
//    Seq(865.0, 3.3183970623390557, Infinity),
//    Seq(62.0, 4.147288365256938, Infinity),
//    Seq(13.0, 9.271771242472552, Infinity),
//    Seq(860.0, 3.3191732989016103, Infinity),
//    Seq(68.0, 4.144633738937863, Infinity),
//    Seq(937.0, 3.311439819488432, Infinity),
//    Seq(156.0, 3.626120855109313, Infinity),
//    Seq(875.0, 3.3171661847600205, Infinity),
//    Seq(228.0, 3.414301835554011, Infinity),
//    Seq(53.0, 4.437003948677621, Infinity),
//    Seq(60.0, 4.151567466113429, Infinity),
//    Seq(895.0, 3.3131705659650352, Infinity),
//    Seq(71.0, 4.144599680348139, Infinity),
//    Seq(755.0, 3.3372306445972106, Infinity),
//    Seq(149.0, 3.682628983703803, Infinity),
//    Seq(845.0, 3.327998040966529, Infinity),
//    Seq(54.0, 4.432870464872524, Infinity),
//    Seq(848.0, 3.3261690618131876, Infinity),
//    Seq(115.0, 3.8861717161104656, Infinity),
//    Seq(111.0, 3.8914647516200427, Infinity),
//    Seq(199.0, 3.474839911445378, Infinity),
//    Seq(81.0, 4.090105310258212, Infinity),
//    Seq(1033.0, 3.275248574218121, Infinity),
//    Seq(82.0, 4.090063213068989, Infinity),
//    Seq(878.0, 3.3158257428020015, Infinity),
//    Seq(994.0, 3.2868339492141803, Infinity),
//    Seq(1043.0, 3.265826863006412, Infinity),
//    Seq(991.0, 3.2901184163864334, Infinity),
//    Seq(197.0, 3.479479222551049, Infinity),
//    Seq(80.0, 4.09211283878268, Infinity),
//    Seq(317.0, 3.36347472131975, Infinity),
//    Seq(127.0, 3.83074383108848, Infinity),
//    Seq(37.0, 4.948554048962997, Infinity),
//    Seq(996.0, 3.2861490432165024, Infinity),
//    Seq(210.0, 3.441711937245792, Infinity),
//    Seq(986.0, 3.292906635914764, Infinity),
//    Seq(1027.0, 3.2780560418807507, Infinity),
//    Seq(61.0, 4.149622087957537, Infinity),
//    Seq(84.0, 4.087050743714275, Infinity),
//    Seq(30.0, 4.948688407205287, Infinity),
//    Seq(315.0, 3.371323789519157, Infinity),
//    Seq(896.0, 3.312780202920983, Infinity),
//    Seq(1037.0, 3.2732643050034644, Infinity),
//    Seq(1053.0, 3.2588630438850092, Infinity),
//    Seq(1036.0, 3.273812185364626, Infinity),
//    Seq(951.0, 3.302808156071956, Infinity),
//    Seq(847.0, 3.32649103405787, Infinity),
//    Seq(125.0, 3.8345462865386244, Infinity),
//    Seq(749.0, 3.338149065756951, Infinity),
//    Seq(41.0, 4.618386680740448, Infinity),
//    Seq(42.0, 4.611458967028852, Infinity),
//    Seq(998.0, 3.284806491290426, Infinity),
//    Seq(758.0, 3.33390534375302, Infinity),
//    Seq(172.0, 3.548226998555357, Infinity),
//    Seq(155.0, 3.6328713472776872, Infinity),
//    Seq(893.0, 3.313198046745887, Infinity),
//    Seq(943.0, 3.3109410841224904, Infinity),
//    Seq(316.0, 3.365700626104461, Infinity),
//    Seq(91.0, 3.9559288213087758, Infinity),
//    Seq(51.0, 4.446287453020024, Infinity),
//    Seq(72.0, 4.141704132967757, Infinity),
//    Seq(1000.0, 3.2838846626017277, Infinity),
//    Seq(203.0, 3.4646545151814534, Infinity),
//    Seq(770.0, 3.3305456172860373, Infinity),
//    Seq(96.0, 3.9462755955581876, Infinity),
//    Seq(150.0, 3.675707040481205, Infinity),
//    Seq(112.0, 3.887988979632415, Infinity),
//    Seq(704.0, 3.3510590214286413, Infinity),
//    Seq(198.0, 3.4757082979087155, Infinity),
//    Seq(119.0, 3.882268958294473, Infinity),
//    Seq(120.0, 3.8388769015509068, Infinity),
//    Seq(13484.0, 2.388043918046784, Infinity),
//    Seq(94.0, 3.94930871007174, Infinity),
//    Seq(55.0, 4.426735444172594, Infinity),
//    Seq(85.0, 4.085393410081545, Infinity),
//    Seq(12127.0, 3.1958257930567404, Infinity),
//    Seq(145.0, 3.71177338623772, Infinity),
//    Seq(1018.0, 3.2791136999280353, Infinity),
//    Seq(189.0, 3.523401765212351, Infinity)
//  )
//
//  println((l zip CrowdingDistance.apply(l)).mkString("\n"))
//
//}
