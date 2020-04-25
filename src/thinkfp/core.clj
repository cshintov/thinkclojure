(ns thinkfp.core
  (:require [thinkfp.ch3 :as ch3])
  (:gen-class))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (ch3/draw-grid 10 18 3))
