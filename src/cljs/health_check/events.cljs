(ns health-check.events
  (:require [re-frame.core :as re-frame]
            [ajax.core :refer [GET]]
            [health-check.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn  [_ _]
   db/default-db))

(defn set-active-pannel [db [_ active-panel]]
   (assoc db :active-panel active-panel))

(re-frame/reg-event-db
 ::set-active-panel
 set-active-pannel)

(defn update-service-health [db [_ health-info]]
  (assoc-in db [:health-check-results (str (:env health-info) (:service-name health-info))] health-info))

(re-frame/reg-event-db
 ::set-health-check-status
 update-service-health)

(defn check-service-health [db [_ [url service]]]
  (GET url
       ;; dispactching the callback means they will get the latest version of db which may have changed
       {:handler #(re-frame/dispatch [::set-health-check-status (assoc service :healthy? true)])
        :error-handler #(re-frame/dispatch [::set-health-check-status (assoc service :healthy? false)])})
  ;; MUST RETURN DB HERE!
  db)

(re-frame/reg-event-db
 ::check-service-health
 check-service-health)

(defn add-service-to-monitoring [db [_ service]]
  (assoc-in db [:monitored-services (:url service)] service))

(re-frame/reg-event-db
 ::add-service-to-monitoring
 add-service-to-monitoring)
