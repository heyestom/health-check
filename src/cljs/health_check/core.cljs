(ns health-check.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [health-check.events :as events]
            [health-check.routes :as routes]
            [health-check.views :as views]
            [health-check.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (routes/app-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
