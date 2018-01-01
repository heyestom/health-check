(ns health-check.events-test
  (:require [health-check.events :as events]
            [cljs.test :refer-macros [deftest testing is]]))

(deftest setting-the-current-view-pannel
  (testing "given an empty db it assoc an active view into the db"
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

(deftest test-setting-service-health-check-status
  (testing "given an empty db it will add service health into the db"
    (let [db      {}
          service-heath {:env "CI" :service-name "Test Service" :other-info 123}
          event   [::events/set-health-check-status service-heath :event]
          expected-db  {:health-check-results {"CITest Service" service-heath}}
          result-db (events/update-service-health db event)]
      (is (= expected-db result-db))))

  (testing "given a db with an existing health check result it will append new service healths"
    (let [db       {:health-check-results {"An exisitng service" {:some-health "some val"}}}
          service-heath {:env "CI" :service-name "Test Service" :other-info 123}
          event   [::events/set-health-check-status service-heath :event]
          expected-db  {:health-check-results {"An exisitng service" {:some-health "some val"}
                                               "CITest Service" service-heath}}
          result-db (events/update-service-health db event)]

      (is (= expected-db result-db))))

  (testing "given a db with existing health check results it will update old heathchecks with new ones"
    (let [db       {:health-check-results {"CITest Service" {:some-health "some old val"}}}
          new-service-heath {:env "CI" :service-name "Test Service" :other-info 123}
          event   [::events/set-health-check-status new-service-heath :event]
          expected-db  {:health-check-results {"CITest Service" new-service-heath}}
          result-db (events/update-service-health db event)]

      (is (= expected-db result-db)))))

(deftest checking-a-services-health
  (testing "will make an ajax call to find the services health and updat the db accordingly"
    (let [service-url "https://some-service-url.com"
          db       {}
          service {:url "https://some-service-url.com" :env "CI" :service-name "Test Service" :other-info 123}
          event   [::events/check-service-health service :event]
          expected-db  {:monitored-services {service-url service}}
          result-db (events/add-service-to-monitoring db event)]
      (is (= "not finished" result-db)))))

(deftest adding-a-service-to-monitoring
  (testing "given there are no services currently being monitored a new service to be monitored will be added"
    (let [db      {}
          service-url "https://some-service-url.com"
          service {:url "https://some-service-url.com" :env "CI" :service-name "Test Service" :other-info 123}
          event   [::events/add-service-to-monitoring service :event]
          expected-db  {:monitored-services {service-url service}}
          result-db (events/add-service-to-monitoring db event)]
      (is (= expected-db result-db))))

  (testing "given there are services currently being monitored adding a new service will not overwirte them"
    (let [db       {:monitored-services {"some-existing-service" {:some-monitoring-info nil}}}
          service-url "https://some-service-url.com"
          service {:url "https://some-service-url.com" :env "CI" :service-name "Test Service" :other-info 123}
          event   [::events/add-service-to-monitoring service :event]
          expected-db  {:monitored-services {"some-existing-service" {:some-monitoring-info nil}
                                             service-url service}}
          result-db (events/add-service-to-monitoring db event)]
      (is (= expected-db result-db))))

  (testing "if a service already exists then it will be updated with the latest information"
    (let [service-url "https://some-service-url.com"
          db       {:monitored-services {service-url {:some-monitoring-info nil}}}
          service {:url "https://some-service-url.com" :env "CI" :service-name "Test Service" :other-info 123}
          event   [::events/add-service-to-monitoring service :event]
          expected-db  {:monitored-services {service-url service}}
          result-db (events/add-service-to-monitoring db event)]
      (is (= expected-db result-db)))))
