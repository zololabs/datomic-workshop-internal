(ns cms.domain.article
  (:require [datomic.api :as api]
            [cms.datomic.core :as db]))

(defn create [title body category]
  (let [t (api/tempid :db.part/user)
        a {:article/title title
           :article/body body
           :article/category category
           :db/id t}
        {:keys [db-after tempids]} (->> a
                                        vector
                                        (api/transact db/CONN)
                                        deref)
        ar-id (api/resolve-tempid db-after tempids t)]
    (->> ar-id
         (api/entity db-after)
         api/touch)))

(defn find-all []
  (->> (api/q '[:find ?e :where [?e :article/title]] (api/db db/CONN))
       (map #(api/entity (api/db db/CONN) (first %)))
       (map api/touch)))

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
