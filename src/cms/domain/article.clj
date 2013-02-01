(ns cms.domain.article
  (:require [datomic.api :as api]
            [cms.datomic.core :as db]
            [zolodeck.demonic.core :as demonic]))

(defn find-all []
  (->> (demonic/run-query '[:find ?e :where [?e :article/title]])
       (map #(demonic/load-entity (first %)))))

;; (defn find-by-email [email]
;;   (->> (api/q '[:find ?e :in $ ?email :where [?e :article/email ?email]] (api/db db/CONN) email)
;;        (map #(api/entity (api/db db/CONN) (first %)))
;;        (map api/touch)
;;        first))

;; (defn update [a name]
;;   (->> [:db/add (:db/id a) :article/name name]
;;        vector
;;        (api/transact db/CONN)
;;        deref))

;; (defn delete [a]
;;   (->> [:db.fn/retractEntity (:db/id a)]
;;        vector
;;        (api/transact db/CONN)
;;        deref))
