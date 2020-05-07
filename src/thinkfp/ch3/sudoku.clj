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
;; ex:
;; 4.....8.5.3..........7......2.....6.....8.4......1.......6.3.7.5..2.....1.4......


(def digits "123456789 ")

(defn make-cell [value]
  ((cl/make-cell-with-materials "+" "|" "-") [value]))

(dw/draw-cell (make-cell [" "]))

(ut/foreach 
  #(dw/draw-cell (make-cell %) :top true)
  (map vector digits))

(def config1 (first (ut/lazyfile "resources/hard.txt")))

(defn parse [config]
  "Returns the grid from the config"
  (let [config (st/replace config #"\.|0" " ")
        flat-index (partial ut/flat-index 9)] 
    (assert (= 81 (count config)))
    (for [i (range 9)] 
      (for [j (range 9)]
        (make-cell (nth config (flat-index i j)))))))

(dw/draw-grid (parse config1))

;; What's the idea here?
;; Backtracking and search.
;; How? 
;; First find out all the possible values for each empty cell.
;; Find cells with only one possibility and assign that value. (value)
;; Find the value which is possible in only cell and assign that value. (cell)
;; Each assignment has to update the possible values in each cell by eliminating
;; the assigned value.
;; Now the search:
;; Take the cell with minimum possibilities try one by one. Choose the others if 

;; the current selection leads to a contradiction.
