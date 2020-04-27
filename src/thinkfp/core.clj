(ns thinkfp.core
  (:require [thinkfp.ch3.grid :as gd] 
            [thinkfp.ch3.draw :as dw]
            [thinkfp.ch3.cell :as cl])
  (:gen-class))

(defn -main
  "Think Python exercises in Clojure"
  [& args]

  (let 
    [mkcl (cl/make-cell-with-materials "+" "-" "|" "r")
     cell (mkcl 2 2)
     mkgd (gd/make-grid-with-cell cell)] 
    (dw/draw-grid (mkgd 5 5))))

;(grd/draw-grid 15 24 2)))
;(-main)
