(ns thinkfp.core
  (:require [thinkfp.ch3.ttt :as tt] 
            [thinkfp.ch3.draw :as dw]
            [thinkfp.ch3.cell :as cl])
  (:gen-class))

(defn -main
  "Think Python exercises in Clojure"
  [& args]
  
  (tt/play-a-game))

;(grd/draw-grid 15 24 2)))
;(-main)
