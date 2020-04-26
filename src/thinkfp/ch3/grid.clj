(ns thinkfp.ch3.grid
  (:require [thinkfp.utils :as ut]
            [clojure.pprint :as pp]))

;;------------------------------------------------------------------------
;; Base layer where components of the grid is constructed
;; Like ceiling, wall_layer, row, etc.

(defn make-component [joint mater len]
  "Makes a component of length len
  (make-component + - 3)
  => (+ - - -)"
  (concat [joint] (repeat len mater)))

(defn join-components [mater joint len]
  "Joins components and finish it with a joint
  (join-components row floor 3)
  => (row row row floor)"
  (concat (repeat len mater) [joint]))

(defn make-tile [joint mater]
  "ex: + - - -"
  (partial make-component joint mater))

(defn make [tile col]
  "Makes a ceiling or a wall layer
    For ex:
    A ceiling is a series of tiles ending with +.
    + - - - + - - - +"
  (let [joint (first tile)]
    (join-components tile joint col)))

(defn make-row [ceil-tile wall-tile layers]
  "A row is one ceiling and 'len' of wall-layers"
    (make-component ceil-tile wall-tile layers))

;;------------------------------------------------------------------------
;; Drawing functions

(defn draw [elem]
  (-> elem
      flatten
      ut/join
      println))

(defn draw-row [row] 
  (ut/foreach draw row))

;;------------------------------------------------------------------------
;; Grid representation

;; Initial version of this program used an actual grid with each component 
;; (ceiling and walls) in a 2d list. The refactored version, uses a map 
;; to represent the grid. For example, A grid can be represented by
;; {:ceil ("+" "-" "-") :wall ("|" "r" "r") :row 5, :col 5, :len 2} 

(defn make-grid [ceil wall len row col]
  {:ceil ceil :wall wall :len len :row row :col col})

;; Accepts the grid representation as a map and fleshes it out.
(defn draw-grid [grid]
  (let [{ceil :ceil wall :wall row :row col :col len :len} grid
        ceiling (make ceil col) 
        layer (make wall col)
        a_row (make-component ceiling layer len)
        rows (repeat row a_row)]
    (ut/foreach draw-row rows)
    (draw ceiling)))

;; Prepares for grid creation with given building materials.
;; The output is a particular version of the 'make-grid' function ,  
;; pre-cooked with the given materials.
(defn make-grid-with-materials 
  [ceil-joint ceil-mater wall-joint wall-mater len]
  (let 
    [ceil ((make-tile ceil-joint ceil-mater) len)
     wall ((make-tile wall-joint wall-mater) len)]
    (partial make-grid ceil wall len)))
