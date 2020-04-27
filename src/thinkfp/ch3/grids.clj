(ns thinkfp.ch3.grids
  (:require [thinkfp.utils :as ut]
            [thinkfp.ch3.cell :as cl]
            [thinkfp.ch3.draw :as dw]
            [thinkfp.ch3.grid :as gd]
            [clojure.pprint :as pp]))

(def mkcl (cl/make-cell-with-materials "+" "-" "|" "r"))
(def cell (mkcl 2 2))
(def mkgd (gd/make-grid-with-cell cell))
(dw/draw-grid  (mkgd 5 5))

(def mkcl (cl/make-cell-with-materials "+" "-" "|" "r"))
(def nc (mkcl 1 1))

(def mkcla (cl/make-cell-with-materials "+" "-" "|" "x"))
(def mc (mkcla 1 1))

(def mkcl (cl/make-cell-with-materials "+" "-" "|" "r"))
(dw/draw-grid (gd/make-grid (mkcl 2 2) 5 5))

(dw/draw-row [nc mc] :top true)
(dw/draw-grid [[nc mc nc]
            [nc mc nc]
            [nc mc nc]])

(cl/make-row [nc mc nc])

;; TODO
;; Make a grid with random characters.
