(ns thinkfp.ch3.sudoku
  (:require 
    [thinkfp.utils :as ut] 
    [thinkfp.ch3.draw :as dw]
    [thinkfp.ch3.cell :as cl]
    [thinkfp.ch3.grid :as gd]
    [clojure.string :as st]
    [clojure.pprint :as pp]))

;; ----------------------------------------------------------------------------
;; The sudoku.

;; Let's actually solve the puzzle.
;; But first things, first. Parsing the initial configuration into a grid.


;; Given a configuration parse it into the grid.

(defn make-cell-with [ceil-joint ceil-mater wall-joint] 
  (fn [value] 
    (let 
      [make #(cl/make-cell-with-materials ceil-joint ceil-mater wall-joint %)]
      ((make value) 1 1))))

(def make-cell (make-cell-with "+" "-" "|"))

(def digits "123456789 ")

(dw/draw-cell (make-cell "2") :top true)

(ut/foreach 
  #(dw/draw-cell (make-cell %) :top true)
  digits)


(def config1 (first (ut/lazyfile "resources/hard.txt")))

(defn parse [config]
  "Returns the grid from the config"
  (let [config (st/replace config #"\." " ")
        flat-index (partial ut/flat-index 9)] 
    (assert (= 81 (count config)))
    (for [i (range 9)] 
      (for [j (range 9)]
        (make-cell (nth config (flat-index i j)))))))

(dw/draw-grid (parse config1))
