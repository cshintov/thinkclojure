(ns thinkfp.utils
  (:require [utilities.utils :as global :refer :all]))

(defn foreach [f coll]
  (dorun (map f coll)))
