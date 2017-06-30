(defproject com.yetanalytics/datomic-riemann "0.1.1-SNAPSHOT"
  :description "Tiny lib to send metrics events from datomic to Riemann"
  :url "https://github.com/yetanalytics/datomic-riemann"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0" :scope "provided"]
                 [riemann-clojure-client "0.4.5"]
                 [org.clojure/tools.logging "0.4.0"]]
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[ch.qos.logback/logback-classic "1.2.3"]]
                   :source-paths ["src" "dev"]}})
