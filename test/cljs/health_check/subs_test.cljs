(ns health-check.subs-test
  (:require [health-check.subs :as subs]
            [cljs.test :refer-macros [deftest testing is]]))

(deftest active-panel
  (testing "will get the active panel from db"
    (let [db {:active-panel :test-panel}]
      (is (= :test-panel (subs/active-panel db nil))))))

(deftest health-check-results
  (testing "will get the active panel from db"
    (let [service-health {"CITest Service"
                                     {:env "CI"
                                      :service-name "Test Service"
                                      :other-info 123}}
          db {:health-check-results service-health}]
      (is (= service-health (subs/health-check-results db nil))))))

(deftest monitored-services-subscription
  (testing "will get the services to monitor from the app state db"
    (let [test-service-url "https://my-test-service.com"
          services  {test-service-url
                     {:env "CI"
                      :service-name "Test Service"
                      :url test-service-url}}
          db {:monitored-services services}]
      (is (= services (subs/monitored-services db nil))))))
