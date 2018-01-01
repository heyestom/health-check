(ns health-check.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [health-check.core-test]
              [health-check.events-test]
              [health-check.subs-test]))

(doo-tests 'health-check.core-test
           'health-check.events-test
           'health-check.subs-test)
