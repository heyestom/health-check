(ns health-check.views.view-decider
  (:require [health-check.subs :as subs]
            [health-check.views.about-page :refer [about-panel]]
            [health-check.views.monitoring-page :refer [monitoring-panel]]
            [health-check.views.settings-page :refer [settings-panel]]
            [re-frame.core :as re-frame]))

;; main
(defn- panels [panel-name]
  (case panel-name
    :monitoring-panel [monitoring-panel]
    :about-panel [about-panel]
    :settings-panel [settings-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [show-panel @active-panel]))
