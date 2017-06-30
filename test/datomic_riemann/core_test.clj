(ns datomic-riemann.core-test
  (:require [clojure.test :refer :all]
            [datomic-riemann.core :refer :all]))

(deftest datomic->riemann-test
  (is (= [{:service "datomic AvailableMB", :metric 921.0, :state "ok"}
          {:service "datomic HeartbeatMsec count", :metric 11, :state "ok"}
          {:service "datomic HeartbeatMsec hi", :metric 5001, :state "ok"}
          {:service "datomic HeartbeatMsec lo", :metric 5000, :state "ok"}
          {:service "datomic HeartbeatMsec sum", :metric 55009, :state "ok"}]
         (datomic->riemann {:HeartbeatMsec {:lo 5000, :hi 5001, :sum 55009, :count 11}, :AvailableMB 921.0}))))
