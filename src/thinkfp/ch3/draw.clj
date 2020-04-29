(ns thinkfp.ch3.draw
  (:require [thinkfp.ch3.cell :as cl]
            [thinkfp.ch3.grid :as gd]
            [thinkfp.utils :as ut]))

;; ------------------------------------------------------------------------
;; Drawing functions

;; Draw a ceiling or a wall.
;; (+ - +) prints as
;;  + - +
;; Join it with spaces and print.
(defn draw-comp [component]
  (-> component
      ut/join-with-space
      println))

;; To draw a cell first we draw the ceiling, the walls and
;; finish it with the floor (aka, ceiling).
;; Draw the ceiling only for top cells.
(defn draw-cell 
  "Draw a cell.  
  Drawing the ceiling is controlled by :top keyword argument"
  [{:keys [ceil wall h top]} &
    {:keys [top] :or {top false}}]
  (if top (draw-comp ceil))
  (ut/foreach draw-comp (repeat h wall))
  (draw-comp ceil))

;; To draw a row of cells first combine the cells to form a row
;; and then draw it (which is internally like a big cell).
(defn draw-row 
  "Draw a row specified by cells. Only the ceiling of the top row
  is drawn which is controlled by :top keyword argument"
  [cells & {:keys [top] :or {top false}}]
  (let [row (cl/make-row cells)]
    (draw-cell row :top top)))

;; Given a grid of cells, draw it by drawing first the top row 
;; and then the rest of the rows.
(defn draw-grid [grid]
  (draw-row (first grid) :top true)
  (ut/foreach draw-row (rest grid)))
