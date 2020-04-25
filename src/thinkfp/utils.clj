(ns thinkfp.utils
  (:require [utilities.utils :refer :all]
            [clojure.string :as string]))

(defn foreach [f coll]
  (dorun (map f coll)))

(defn join [component]
  "Draw a component"
  (string/join " " component))
