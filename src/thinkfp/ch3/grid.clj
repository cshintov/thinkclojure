(ns thinkfp.ch3.grid
  (:require [thinkfp.utils :as ut]
            [clojure.pprint :as pp]))

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

;; Drawing functions
(defn draw [elem]
  (-> elem
      flatten
      ut/join
      println))

(defn draw-row [row] 
  (ut/foreach draw row))

;; Grid construction
(defn make-grid [ceil wall len row col]
  "A grid is represented as a map of the following elements
  (ceil, wall, row, col, len)"
  {:ceil (ceil col) :wall (wall col) :row row :col col :len len})

(defn draw-grid [grid]
  (let [{ceil :ceil wall :wall row :row len :len} grid
        floor ceil
        a_row (make-component ceil wall len)
        rows (repeat row a_row)]
    (ut/foreach draw-row rows)
    (draw floor)))

(defn make-grid-with-materials 
  [ceil-joint ceil-mater wall-joint wall-mater len]
  (let 
    [ceil-tile ((make-tile ceil-joint ceil-mater) len)
     wall-tile ((make-tile wall-joint wall-mater) len)
     ceil (partial make ceil-tile)
     wall (partial make wall-tile)]
    (partial make-grid ceil wall len)))
