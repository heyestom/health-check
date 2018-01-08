(ns health-check.health
  (:require [health-check.events :as events]
            [health-check.subs :as subs]

            [re-frame.core :as re-frame]))

(defn ping-health-check-endpoints [services-to-monitor]
  (doall
   (map #(re-frame/dispatch [::events/check-service-health %]) services-to-monitor)))

;; useful functions for building the monitoring UI
(defn- random-health []
  (> (rand-int 30) 10))

;; stand in for making web requests....
(defn- stubbed-ping-health-check-endpoints [services-to-monitor]
  (do
    (re-frame/dispatch
     [::events/set-health-check-status
      {:service-name "very-important-service" :env "qa" :severity 2 :healthy? (random-health)}])

    (re-frame/dispatch
     [::events/set-health-check-status
      {:service-name "big-flakey-service" :env "qa" :severity 2 :healthy? (random-health)}])

    (re-frame/dispatch
     [::events/set-health-check-status
      {:service-name "some-thrid-service" :env "qa" :severity 2 :healthy? (random-health)}])

    (re-frame/dispatch
     [::events/set-health-check-status
      {:service-name "bob-service" :env "qa" :severity 2 :healthy? (random-health)}])

    (re-frame/dispatch
     [::events/set-health-check-status
      {:service-name "payment-process-service" :env "qa" :severity 2 :healthy? (random-health)}])

    (re-frame/dispatch
     [::events/set-health-check-status
      {:service-name "my-db--service" :env "qa"  :severity 2 :healthy? (random-health)}])

    (re-frame/dispatch
     [::events/set-health-check-status
      {:service-name "very-important-service" :env "prod" :severity 1  :healthy? (random-health)}])

    (re-frame/dispatch
     [::events/set-health-check-status
      {:service-name "big-flakey-service" :env "prod" :severity 1  :healthy? (random-health)}])

    (re-frame/dispatch
     [::events/set-health-check-status
      {:service-name "some-thrid-service" :env "prod" :severity 1  :healthy? (random-health)}])

    (re-frame/dispatch
     [::events/set-health-check-status
      {:service-name "bob-service" :env "prod" :severity 1  :healthy? (random-health)}])

    (re-frame/dispatch
     [::events/set-health-check-status
      {:service-name "payment-process-service" :env "prod" :severity 1  :healthy? (random-health)}])

    (re-frame/dispatch
     [::events/set-health-check-status
      {:service-name "my-db--service" :env "prod"  :severity 1  :healthy? (random-health)}])

    (re-frame/dispatch
     [::events/set-health-check-status
      {:service-name "very-important-service" :env "ci" :severity 3 :healthy? (random-health)}])

    (re-frame/dispatch
     [::events/set-health-check-status
      {:service-name "big-flakey-service" :env "ci" :severity 3 :healthy? (random-health)}])

    (re-frame/dispatch
     [::events/set-health-check-status
      {:service-name "some-thrid-service" :env "ci" :severity 3 :healthy? (random-health)}])

    (re-frame/dispatch
     [::events/set-health-check-status
      {:service-name "bob-service" :env "ci" :severity 3 :healthy? (random-health)}])

    (re-frame/dispatch
     [::events/set-health-check-status
      {:service-name "payment-process-service" :env "ci" :severity 3 :healthy? (random-health)}])

    (re-frame/dispatch
     [::events/set-health-check-status
      {:service-name "my-db--service" :env "ci"  :severity 3 :healthy? (random-health)}])))

(defn start-monitoring []
  (let [services-to-monitor (re-frame/subscribe [::subs/monitored-services])]
    (js/setInterval
     #(ping-health-check-endpoints @services-to-monitor)
     (* 1000 10))))
