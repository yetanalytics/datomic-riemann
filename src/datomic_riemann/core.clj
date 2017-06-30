(ns datomic-riemann.core
  (:require [riemann.client :as r]
            [clojure.tools.logging :as log])
  (:gen-class))

(def client
  (delay
   (log/info "Initializing Riemann client...")
   (let [host (System/getenv "RIEMANN_HOST")
         port (some-> (System/getenv "RIEMANN_PORT")
                      Long/parseLong)]
     (assert host "RIEMANN_HOST is required!")
     (log/infof "Starting Riemann Client, Host: %s Port: %s Custom Event Host: %s" host port (System/getenv "RIEMANN_EVENT_HOST"))
     (let [c (r/tcp-client (cond-> {:host host}
                             port (assoc :port port)))]
       (if (r/connected? c)
         (log/info "Riemann metrics client connected!")
         (log/warn "Riemann metrics client could not connect. Metrics events will be DROPPED!"))
       c))))

(defn datomic->riemann
  "Convert a datomic batch metrics map into a vector of Riemann events ordered
   by service"
  [metrics]
  (let [?custom-host (System/getenv "RIEMANN_EVENT_HOST")]
    (into []
          (sort-by :service
           (for [[k v] metrics
                 :let [svc-base (str "datomic " (name k))]
                 [service metric] (if (map? v)
                                    (into {}
                                          (for [[k' v'] v]
                                            [(str svc-base " " (name k'))
                                             v']))
                                    [[svc-base v]])]
             (cond-> {:service service
                      :metric metric
                      :state "ok"}
               ?custom-host (assoc :host ?custom-host)))))))

(defn send-metrics [metrics]
  (let [c @client]
    (log/debugf "Sending metrics: %s" metrics)
    (if (r/connected? c)
      (r/send-events c
                     (datomic->riemann metrics))
      (log/warnf "Metrics client not connected. Dropping event: %s" metrics))))
