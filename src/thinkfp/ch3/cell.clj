(ns thinkfp.ch3.cell
  (:require [thinkfp.utils :as ut]))

;; ------------------------------------------------------------------------
;; Let's talk about cells.

;; The below Cell 
;; + - - +
;; | 1 2 |
;; | 3 4 |
;; + - - +
;; can be represented as
;; {:corner "+", :wall "|", :tile "-",
;;  :contents [[1 2] [3 4]]}


;; make a ceiling
(defn make-ceil [w corner tile]
  (flatten [corner (repeat w tile) corner]))

;; A cell is '+' + w * tile + '+'
;;           '|' + row1     + '|'
;;           '|' + row2     + '|'
;;            ..................
;;           '+' + w * tile + '+'

(defn make-cell [corner wall tile [fst :as contents]]
  (let [h (count contents) w (count fst)
        ceil (make-ceil w corner tile)
        rowify #(flatten [wall % wall])
        rows (map rowify contents)]
    (concat (cons ceil rows) [ceil])))

;; contents will be supplied later
(defn make-cell-with-materials [corner wall tile]
  (partial make-cell corner wall tile))

;; ----------------------------------------------------------------------------
;; If we are to build bigger constructs than a tiny cell, 
;; we need to combine them. In order to combine cells, we need to combine their
;; individual components, the ceilings and walls.

;; Let's combine components.
;; (+ - +) combine (* r *) ==> (+ - + r *)
;; We drop the first of the second component. In this case '*'

(defn combine-components [ca cb]
  (concat ca (rest cb)))

(defn combine-cells [ca cb]
  "Combine cells ca and cb"
  (map combine-components ca cb))

;; ------------------------------------------------------------------------
;; Now that we can combine them, let's create a row of cells.

;; A row is just a horizontal combination of cells.
;; It's representation is analogous to a cell's representation 
;; with ceilings and walls.
;;
;; {:ceil ("+" "-" "+" "-" "+" "-" "+"), 
;;  :wall ("|" "r" "|" "x" "|" "r" "|"), 
;;  :h 1, :w 1}

(defn make-row [cells]
  (reduce combine-cells cells))
