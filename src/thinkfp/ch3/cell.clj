(ns thinkfp.ch3.cell
  (:require [thinkfp.utils :as ut]))

;; ------------------------------------------------------------------------
;; Let's talk about cells.
;; It has ceiling, wall, width and height.
;;
;; A unit cell. (dimension 1 X 1)
;;  ceil = + - +
;;  wall = |   |
;;         + - +
;; 
;; A Cell is represented as {:ceil :wall :h :w}
;; {:ceil ("+" "-" "+"), 
;;  :wall ("|" "r" "|"), 
;;  :h 1, :w 1}

(defn make-cell [ceil wall h w]
  "Given ceiling, wall, height and width, construct a cell.
  ceiling and wall can be either their list represenation or
  a function which will generate it."
  {:ceil (if (fn? ceil) (ceil w) ceil)
   :wall (if (fn? wall) (wall w) wall)
   :h h :w w})

;; In order to create ceilings and walls, we will have to combine
;; the components making them. Let's call the components joint and mater.

(defn make-comp [mater joint width]
  "Make a component by joining materials and joints
  => ("+" "-" "+")"
  (concat [joint] (repeat width mater) [joint]))

;; The below function makes it possible to create a cell as below.
;;
;; (def mk-cell (make-cell-with-materials "+" "-" "|" "r"))
;; (def cell (mk-cell 1 1))

(defn make-cell-with-materials [ceil-joint ceil-mater wall-joint wall-mater]
  "Returns a function when called as (f h w) creates a cell with 
  the given materials and dimensions (h w)"
  (fn [h w]
    (let [ceil (make-comp ceil-mater ceil-joint w)
          wall (make-comp wall-mater wall-joint w)]
          (make-cell ceil wall h w))))

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
  (let [{c1 :ceil w1 :wall h1 :h wi1 :w} ca
        {c2 :ceil w2 :wall h2 :h wi2 :w} cb
        nuc (combine-components c1 c2)
        nuw (combine-components w1 w2)]
    (make-cell nuc nuw h1 wi1)))

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
