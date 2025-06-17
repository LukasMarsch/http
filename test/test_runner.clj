(ns test-runner
  (:require [clojure.test :refer :all]
            [http.header-test]))

(defn -main []
  (run-all-tests))
