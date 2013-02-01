(ns cms.datomic.core
  (:require [datomic.api :as api]
            [cms.datomic.schema :as schema]
            [zolodeck.demonic.core :as demonic]))

(def uri "datomic:mem://cms")

(defn init []
  (demonic/init-db uri (schema/all)))