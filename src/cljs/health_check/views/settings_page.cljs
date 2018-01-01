(ns health-check.views.settings-page
  (:require [health-check.events :as events]
            [health-check.subs :as subs]
            [health-check.utils.csv-parsing :refer [csv->services]]
            [health-check.views.nav :as nav]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent]))

(defn- update-monitored-services [services-to-monitor]
  (doall (map (fn [service]
                (re-frame/dispatch
                 [::events/add-service-to-monitoring service]))
              services-to-monitor)))

(defn- bulk-add []
  (reagent/with-let [input-text (reagent/atom "")]
    [:div.bulk-add
     [:h2 "Bulk add:"]
     [:p "Bulk add in csv format - service name, environment, severity level, url"]
     [:textarea.bulk-add-text-area
      {:rows 5
       :value @input-text
       :on-change #(reset! input-text (.. % -target -value))}]
     [:button.bulk-add-save.pure-button.pure-button-primary
      {:on-click
       #(update-monitored-services (csv->services @input-text))} "Save"]]))

(defn monitored-service [{:keys [env service-name url severity]}]
  [:span.monitored-service
   [:h4.monitored-service-info (str "Service: " env " " service-name " URL: " url " Severity: " severity)]
   ;[:button.pure-button.pure-button-primary.delete-button "Remove"]
   ])

(defn currently-monitoring-list [monitoring-list]
  [:div.monitoring-list
   [:h2 "Currently monitoring:"]
   (map (fn [[k v]]
           ^{:key v} [monitored-service v])
        monitoring-list)])

(defn monitoring-list [monitoring-list]
  [:div.pure-g
   [:div.monitoring-section.pure-u-1-1
    [currently-monitoring-list monitoring-list]
    [bulk-add]]])

(defn settings-panel []
  (let [currently-monitoring (re-frame/subscribe [::subs/monitored-services])]
    [:div
     [nav/navigation-bar]
     [monitoring-list @currently-monitoring]]))
