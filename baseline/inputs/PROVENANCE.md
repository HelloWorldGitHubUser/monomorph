# Input provenance

All files under `baseline/inputs/<app>/monolith` and `decomposition.input-kit.json` are verbatim copies (`git archive`) of
`HelloWorldGitHubUser/input-kit` at commit `b3e80d2813373621a1110e6a57435d13ea1d6e6e` (release `agent-input-v8`). They are never edited;
the changes MonoMorph needs are applied to copies by `baseline/scripts/prepare_inputs.py`.

The git tree/blob ids below identify the copied content exactly (`git rev-parse <commit>:<path>` in input-kit).

| app | input-kit path | git object id |
|---|---|---|
| booking | `booking/booking-monolith` | `db5bf69837ef9e0d30e57dd20db0917b8908f1b2` |
| booking | `booking/booking.json` | `722f65f6560a5b487a96133582372ca8e923633a` |
| ecommerce | `ecommerce/ecommerce-monolith` | `763fc3a984f627a213fd2c856f1024dd1f122174` |
| ecommerce | `ecommerce/ecommerce.json` | `b1bef5a749be6306547ecab89c8fbc5c12fb76a5` |
| goodskill | `goodskill/goodsKill-monolith` | `29b710a786bb438651cd187ae22b2cc4e7ea0b9a` |
| goodskill | `goodskill/goodskill.json` | `b22531f35b5ed4396ca3d3632bac23ec3053f183` |
| gulimall | `gulimall/gulimall-monolith` | `c698f16d06d17b11c0d52ffcc5d08887c8b3de64` |
| gulimall | `gulimall/gulimall.json` | `fd5d0db50ef6e99e3aa08c83b01a22784de69e47` |
| lakeside | `lakeside-mutual/lakeside-mutual-monolith` | `86cb7f04455e5a1287d570fab0cc19dc6cd45775` |
| lakeside | `lakeside-mutual/lakeside.json` | `d724b297c9250e95b225fd233712ad7ba194e2dd` |
| newbee | `newbee-mall/newbee-mall-api` | `99ab98b8c5b15f011ea8ee0757d156ed2d1c10fa` |
| newbee | `newbee-mall/newbee-mall.json` | `0f1dcc54f4d6e59491c14f07b8b6cde1270e6aa5` |
| passjava | `passjava/PassJava-Platform-monolith` | `d841e2d0a783d9a04c62a49c3a3e1e8f2bbef924` |
| passjava | `passjava/PassJava-Platform.json` | `9beae02c7d63c9fdd9d228233e6f61187e959d62` |
| petclinic | `spring-petclinic/spring-petclinic-angularjs` | `97962be7f9171e43a48d3339461c2c718091a419` |
| petclinic | `spring-petclinic/spring-petclinic.json` | `261fc5364761dbf75fdb21d6ad6ebe80b2163f74` |
| youlai | `youlai-mall/youlai-mall-mono` | `f5a33f0ada30367841e04d80fbb023f47009b754` |
| youlai | `youlai-mall/youlai-mall.json` | `f49cd018d40fccafdc7830a61b2313e80a8e7df5` |
| zlt | `zlt-platform/zlt-platform-monolith` | `ca93aae84960be8fe0ecf35589fa0a4b9b8d8a19` |
| zlt | `zlt-platform/zlt-platform.json` | `7447bb4c245f6c2f3a24c07393e9b3b6d65a7793` |
