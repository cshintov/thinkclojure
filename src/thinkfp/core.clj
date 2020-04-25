(ns thinkfp.core
  (:require [thinkfp.ch3 :as ch3])
  (:gen-class))

(defn -main
  "Think Python exercises in Clojure"
  [& args]
  (binding 
    [ch3/ceil-joint "*"
     ch3/wall-joint "|"
     ch3/brick (ch3/make-tile "|" "$")
     ch3/tile (ch3/make-tile "*" "-")]
    (ch3/draw-grid 15 24 2)))
