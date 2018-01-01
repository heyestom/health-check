(ns health-check.events-test
  (:require [health-check.events :as events]
            [health-check.health :as health-checker]
            [cljs.test :refer-macros [deftest testing is]]))

(deftest setting-the-current-view-pannel
  (testing "given a list of services to monitor it will ping the urls and add them to the database if unhealthy"
    (let [db      {}
          active-panel :test-panel
          event   [::events/set-active-panel active-panel :event]
          expected-db  {:active-panel :test-panel}
          result-db (events/set-active-pannel db event)]
      (is (= expected-db result-db))))

  (testing "will update the view when there is existing active-panel"
    (let [db      {:active-panel :existing-panel}
          active-panel :test-panel
          event   [::events/set-active-panel active-panel :event]
          expected-db  {:active-panel :test-panel}
          result-db (events/set-active-pannel db event)]
      (is (= expected-db result-db)))))
