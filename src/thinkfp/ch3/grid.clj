(ns thinkfp.ch3.grid
  (:require [thinkfp.utils :as ut]
            [thinkfp.ch3.cell :as cl]
            [clojure.pprint :as pp]))

;; ------------------------------------------------------------------------
;; Let's talk about The Grid.

;; We represent a grid as a 2d vector of cells.
;;
;;  [[c1 c2] 
;;   [c3 c4]]

;; Now you might be thinking what's a cell?
;; Conceptually, something like this,
;; + - +
;; | r |
;; + - +
;; For details, head over to the cell namespace, nearby.

;; Given a cell and the dimension of the grid we can make use of 
;; the below function to create a grid.

(defn make-grid [cell r c]
  (let [row (repeat c cell)
        grid (repeat r row)]
    grid))

;; Suppose we want to create a grid made with unit cells 
;; + - +
;; | r |
;; + - +
;; The below function bakes another function which given the dimension
;; will draw a grid with the given cell as it's building block.

(defn make-grid-with-cell
  [cell]
  (fn [row col]
    (make-grid cell row col)))
