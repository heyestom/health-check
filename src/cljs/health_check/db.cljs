(ns health-check.db)

;; init state
(def default-db
  {:monitored-services {}
   :health-check-results {}
   :monitoring-interval 999999})
