(ns thinkfp.ch3.grids
  "This module talks about combining grids" 
  (:require [thinkfp.ch3.grid :refer :all]
            [thinkfp.utils :as ut]
            [clojure.pprint :as pp]))

;; Grids is a collection of grids
;; Draw:
;;  1. Construct ceiling using ceilings of each
;;  2. Construct wall using walls of each
;;  3. Draw using draw-grid
;;
;; Or should I refactor the grids's representations to the following
;; (ceil, wall, row, col, len)

(let 
    [mk-grid (make-grid-with-materials "+" "-" "|" "r" 2)] 
    (def _3by3 (mk-grid 3 3))
    (def _3by2 (mk-grid 3 2))
    (def _3by4 (mk-grid 3 4)))

;; While joining will have to drop the joint cell at the end of the first component
(defn join-comp [a b]
  (concat (butlast a) b))

;; Expects the number of rows be same for all the components
(defn join-grids [& grids]
  (let [ceiling (reduce join-comp (map :ceil grids))
        wall (reduce join-comp (map :wall grids))
        row (:row (first grids))
        len (:len (first grids))]
    {:ceil ceiling :wall wall :row row :len len}))

(draw-grid (join-grids _3by3 _3by2 _3by4))
; (draw-grid _3by2)

;; TODO
;; 1. Join vertically
;; 2. Multiply a grid by n
;; 3. Handle uneven grids
