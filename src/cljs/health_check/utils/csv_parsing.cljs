(ns health-check.utils.csv-parsing
  (:require [clojure.string :as string]))

(defn- build-serice-info [[name env severity url]]
  {:service-name name
   :env env
   :severity (js/parseInt severity)
   :url url})

(defn csv->services [csv-data]
  (->> (string/split csv-data  #",")
       (map string/trim)
       (partition 4)
       (map build-serice-info)))
