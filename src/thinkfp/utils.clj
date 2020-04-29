(ns thinkfp.utils
  (:require [utilities.utils :refer :all]
            [clojure.string :as string]))

(defn foreach [f coll]
  "Do f for each of coll"
  (dorun (map f coll)))

(defn join-with-space [component]
  "Join with spaces interspersed"
  (string/join " " component))

(defn at-pos [grid i j]
  "Access element at (i,j) coordinate in a 2d vector/lazy-seq"
  (nth (nth grid i) j))

(defn vectorize-2d [grid]
  (let [vecd (map vec grid)]
    (vec vecd)))

(defn transpose [grid]
  (let 
    [rows (count grid) 
     cols (count (first grid))
     cwise (for [j (range cols) 
                 i (range rows)] 
             (get-in grid [i j]))]
    (partition cols cwise))) 

(defn diagonals [grid]
  (let 
    [rows (count grid) 
     cols (count (first grid))
     pos (for [i (range rows) 
               j (range cols)] [i j]) 
     backward (for [[i j] pos
                    :when (= i j)] 
                (get-in grid [i j])) 
     forward (for [[i j] pos 
                   :when (= (+ i j) (dec rows))] 
               (get-in grid [i j]))]
    [backward forward]))

(defn diag [grid]
  (let 
    [rows (count grid) 
     cols (count (first grid))
     pos (for [i (range rows) 
               j (range cols)] [i j]) 
     backward (for [[i j] pos
                    :when (= i j)] 
                [i j]) 
     forward (for [[i j] pos 
                   :when (= (+ i j) (dec rows))] 
                [i j])]
    [backward forward]))
