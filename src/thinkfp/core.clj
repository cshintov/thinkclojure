(ns thinkfp.core
  (:require [thinkfp.ch3.grid :as grd]
            [thinkfp.ch3.grids :as grds])
  (:gen-class))

(defn -main
  "Think Python exercises in Clojure"
  [& args]
  (let 
    [mk-grid (grd/make-grid-with-materials "+" "-" "|" "r" 2) 
     grid (mk-grid 5 5)] 
    (grd/draw-grid grid)))

;(grd/draw-grid 15 24 2)))
;(-main)
