(ns cms.domain.tag
  (:require [cms.datomic.core :as db]
            [datomic.api :as api]))

(defn create [name]
  (let [tid (api/tempid :db.part/user)
        t {:tag/name name
           :db/id tid}
        {:keys [db-after tempids]} (->> t
                                        vector
                                        (api/transact db/CONN)
                                        deref)
        tag-id (api/resolve-tempid db-after tempids tid)]
    (->> tag-id
         (api/entity db-after)
         api/touch)))