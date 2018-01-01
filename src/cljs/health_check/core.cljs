(ns health-check.core
  (:require [health-check.config :as config]
            [health-check.events :as events]
            [health-check.health :as health]
            [health-check.routes :as routes]
            [health-check.views.view-decider :as view-decider]
            [day8.re-frame.http-fx] ;; causes the :http-xhrio effect handler to self-register with re-frame
            [re-frame.core :as re-frame]
            [reagent.core :as reagent]))

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [view-decider/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (routes/app-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root)
  (health/start-monitoring))
