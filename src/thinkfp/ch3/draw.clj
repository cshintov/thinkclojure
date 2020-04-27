(ns thinkfp.ch3.draw
  (:require [thinkfp.ch3.cell :as cl]
            [thinkfp.ch3.grid :as gd]
            [thinkfp.utils :as ut]))

;; ------------------------------------------------------------------------
;; Drawing functions

(defn draw-comp [component]
  (-> component
      ut/join
      println))

(defn draw-cell 
  [{:keys [ceil wall h top]} &
    {:keys [top] :or {top false}}]
  (if top (draw-comp ceil))
  (ut/foreach draw-comp (repeat h wall))
  (draw-comp ceil))

(defn draw-row 
  [cells & {:keys [top] :or {top false}}]
  (let [row (cl/make-row cells)]
    (draw-cell row :top top)))

(defn draw-grid [grid]
  (draw-row (first grid) :top true)
  (ut/foreach draw-row (rest grid)))
