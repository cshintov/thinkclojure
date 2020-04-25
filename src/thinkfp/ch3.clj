(ns thinkfp.ch3
  (:require [thinkfp.utils :as utils]
            [clojure.string :as string]
            [clojure.pprint :as pp])
  (:gen-class))

(defn make-component [joint mater len]
  "Makes a component of length len
  (make-component - + 3)
  => (+ - - -)"
  (concat [joint] (repeat len mater)))

(defn make-layer [mater joint len]
  "Makes a layer of length len
  (make-layer floor row 3)
  => (row row row floor)"
  (concat (repeat len mater) [joint]))

(def tile
  "+ - - -"
  (partial make-component "+" "-"))

(defn make-ceil [col len]
  "Ceiling is a series of tiles ending with +.
    + - - - + - - - +"
  (make-layer (tile len) "+" col))

(def brick
  "| s s s"
  (partial make-component "|" " "))

(defn make-wall [col len]
  "Wall is a series of bricks ending with |
    | s s s | s s s |"
  (make-layer (brick len) "|" col))

(defn make-row [row col len]
  "A row one layer of ceil and 'len' layers of walls"
  (let [ceil (make-ceil col len)
        wall (make-wall col len)]
    (make-component ceil wall len)))

(defn make-grid [row col len]
  "Generate grid of row's and col's
  It's layers of rows and one floor"
  (let [floor (make-ceil col len)
        rows (make-row row col len)]
    (make-layer rows floor row)))

(defn join [component]
  "Draw a component"
  (string/join " " component))

(defn draw [elem]
  (-> elem
      flatten
      join
      println))

(defn draw-row [row]
  (doseq [elem row]
    (-> elem
        flatten
        join
        println)))

(defn draw-grid [row col len]
  (let [grid (make-grid row col len)]
    (do 
      (dotimes [i (- (count grid) 1)]
        (draw-row (nth grid i))))
      (draw (last grid))))
