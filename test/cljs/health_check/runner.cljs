(ns health-check.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [health-check.core-test]))

(doo-tests 'health-check.core-test)
