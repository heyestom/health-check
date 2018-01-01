(ns health-check.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [health-check.core :as core]))

(deftest fake-test
  (testing "fake description"
    (is (= 2 2))))
