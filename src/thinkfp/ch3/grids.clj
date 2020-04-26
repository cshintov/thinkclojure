(ns thinkfp.ch3.grids
  "This module talks about combining grids" 
  (:require [thinkfp.ch3.grid :refer :all]
            [thinkfp.utils :as ut]
            [clojure.pprint :as pp]))

;; -----------------------------------------------------------------------------
;; Scale a grid horizontally vertically and both

(defn scaleh [grid n]
  (update grid :col #(* % n)))

(defn scalev [grid n]
  (update grid :row #(* % n)))

(defn scale [grid n]
  (let [grdh (scaleh grid n)]
    (scalev grdh n)))

;; -----------------------------------------------------------------------------
;; Add two similar sized grids together horizontally and vertically.

(defn joinv [g1 g2]
  {:ceil (:ceil g1) :wall (:wall g1) :len (:len g1)
   :col (:col g1)
   :row (+ (:row g1) (:row g2))})

(defn joinh [g1 g2]
  {:ceil (:ceil g1) :wall (:wall g1) :len (:len g1)
   :row (:row g1)
   :col (+ (:col g1) (:col g2))})

;; -----------------------------------------------------------------------------
;; Grids is a collection of grids
;; Or should I refactor the grids's representations to the following
;; (ceil, wall, row, col, len)

(let 
    [mk-grid (make-grid-with-materials "+" "-" "|" "r" 1)] 
    (def _1by1 (mk-grid 1 1))
    (def _1by2 (mk-grid 1 2))
    (def _1by3 (mk-grid 1 3))
    (def _2by1 (mk-grid 2 1)))

;; -----------------------------------------------------------------------------
;; While joining will have to drop the joint cell at the end of the first component
(defn join-comp [a b]
  (concat (butlast a) b))

;; TODO
;; 1. Handle uneven grids

;; Combine grids
;; A combo grid: A 2d array of grids
;; Vertical combination of rows
;; Where a row is a horizontal addition of grids.

;; Or what if All of this refactored as in
;; A grid is grid of cells.
