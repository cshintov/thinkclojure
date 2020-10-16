(ns thinkfp.ch3.sudoku
  (:require 
    [thinkfp.utils :as ut] 
    [thinkfp.ch3.draw :as dw]
    [thinkfp.ch3.cell :as cl]
    [thinkfp.ch3.grid :as gd]
    [clojure.string :as st]
    [clojure.pprint :as pp]))

;; ----------------------------------------------------------------------------
;; The sudoku.

;; Let's parse the a puzzle into a grid.

;; Given a configuration parse it into the grid.
;; ex:
;; 4.....8.5.3..........7......2.....6.....8.4......1.......6.3.7.5..2.....1.4......


(def digits "123456789")

(defn make-cell [value]
  ((cl/make-cell-with-materials "+" "|" "-") [value]))

(dw/draw-cell (make-cell [" "]))

(ut/foreach 
  #(dw/draw-cell (make-cell %) :top true)
  (map vector digits))

(def config1 (first (ut/lazyfile "resources/hard.txt")))

(defn parse [config]
  "Returns the grid from the config"
  (let [config (st/replace config #"\.|0" "0")
        flat-index (partial ut/flat-index 9)] 
    (assert (= 81 (count config)))
    (ut/vectorize-2d 
      (for [i (range 9)] 
        (for [j (range 9)] 
          (str (nth config (flat-index i j))))))))
        ;(make-cell (vector (nth config (flat-index i j))))))))


(def parsed (parse config1))
(pp/pprint (first parsed))
(pp/pprint parsed)
; (dw/draw-grid parsed)

;; What's the idea here?
;; Backtracking and search.

;; How? 

;; First find out all the possible values for each empty cell.
;; In order to do this, I need to find the vertical, horizontal and 
;; the box peers of the cell. Then go through each peer group and eliminate
;; all the existing values from digits. Remaining are the possible values for
;; that cell.

(defn group [puzzle strategy] 
  (for [[r c] strategy] 
    (get-in puzzle [r c])))

(defn vertical [size [x y]]
  (for [r (range size)
        :when (not= x r)]
    [r y]))

(defn horizontal [size [x y]]
  (for [c (range size)
        :when (not= y c)]
    [x c]))

(defn box [size [x y]]
  (for [r (range size) c (range size) 
        rm (* (quot x 3)) cm (* (quot y 3))
        :when (not (and (= y c) (= x r)))] 
    [(+ (rm x) r) (+ y (cm c))]))

(def size 3)

(let [size 3 [x y] [0 0]]
  (for [r (range size) c (range size) 
        rm (quot x 3) cm (quot y 3)
        :when (not (and (= y c) (= x r)))] 
    [(+ (* rm 3) r) (+ y (* cm 3))]))

(defn get-peers [puzzle cell]
  (let [size (count puzzle)
        box-size (int (Math/sqrt size))]
    {:vertical (vertical size cell)
     :horizontal (horizontal size cell)
     :box (box box-size cell)}))


(defn find-possible-values-in-a-cell [puzzle cell]
  (let [peers 
        (ut/join-lists (vals (get-peers parsed cell)))]
    (loop [peers (identity peers)
           digits (identity digits)]
      (if (empty? peers)
        digits
        (let [value (get-in puzzle (first peers))]
          (recur (rest peers) 
                 (st/replace digits (str value) "")))))))

(:box (get-peers parsed [1 4]))
(pp/pprint parsed)
(find-possible-values-in-a-cell parsed [0 3])


;; Find cells with only one possibility and assign that value. (value)
;; Find the value which is possible in only cell and assign that value. (cell)
;; Each assignment has to update the possible values in each cell by eliminating
;; the assigned value.
;; Now the search:
;; Take the cell with minimum possibilities try one by one. Choose the others if
;; the current selection leads to a contradiction.
