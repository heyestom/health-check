(ns health-check.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(defn active-panel [db _]
  (:active-panel db))

(re-frame/reg-sub
 ::active-panel
 active-panel)

(defn health-check-results [db _]
  (:health-check-results db))

(re-frame/reg-sub
 ::health-check-results
 health-check-results)

(defn monitored-services [db _]
   (:monitored-services db))

(re-frame/reg-sub
 ::monitored-services
 monitored-services)
